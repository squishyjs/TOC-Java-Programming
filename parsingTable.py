import pandas as pd



def compute_first_and_follow(grammar, terminals):
    # Initialize an empty dictionary to store FIRST sets and FOLLOW sets
    first_sets = {non_terminal: set() for non_terminal in grammar}
    follow_sets = {non_terminal: set() for non_terminal in grammar}

    def compute_first(non_terminal):
        # If the first set is already computed, return it
        if first_sets[non_terminal]:
            return first_sets[non_terminal]
        
        # Iterate over each production rule of the non-terminal
        for production in grammar[non_terminal]:
            # Get the first symbol of the production
            first_symbol = production[0]
            
            # If the first symbol is a terminal, add it to the FIRST set
            if first_symbol in terminals:
                first_sets[non_terminal].add(first_symbol)
            
            # If the first symbol is a non-terminal, recursively compute its FIRST set
            elif first_symbol in grammar:
                first_symbol_first_set = compute_first(first_symbol)
                first_sets[non_terminal].update(first_symbol_first_set - {'ε'})
                
                # If the first symbol can derive ε, check the next symbol in the production
                for i in range(1, len(production)):
                    next_symbol = production[i]
                    if 'ε' in first_symbol_first_set:
                        if next_symbol in terminals:
                            first_sets[non_terminal].add(next_symbol)
                            break
                        elif next_symbol in grammar:
                            next_symbol_first_set = compute_first(next_symbol)
                            first_sets[non_terminal].update(next_symbol_first_set - {'ε'})
                            first_symbol_first_set = next_symbol_first_set
                        if 'ε' not in next_symbol_first_set:
                            break
                    else:
                        break
            
            # If all symbols in the production can derive ε, add ε to the FIRST set
            if all('ε' in compute_first(symbol) if symbol in grammar else symbol == 'ε' for symbol in production):
                first_sets[non_terminal].add('ε')
        
        return first_sets[non_terminal]

    # Function to compute the FOLLOW sets
    def compute_follow():
        # Add $ to the follow set of the start symbol
        start_symbol = next(iter(grammar))  # Start symbol is the first key in the grammar
        follow_sets[start_symbol].add('$')
        
        # Keep updating the FOLLOW sets until no more changes
        while True:
            updated = False
            
            for non_terminal in grammar:
                for production in grammar[non_terminal]:
                    for i in range(len(production)):
                        symbol = production[i]
                        
                        if symbol in grammar:  # If the symbol is a non-terminal
                            # Case 1: Add FIRST(β) to FOLLOW(B) if B -> αBβ
                            if i + 1 < len(production):
                                next_symbol = production[i + 1]
                                if next_symbol in terminals:
                                    if next_symbol not in follow_sets[symbol]:
                                        follow_sets[symbol].add(next_symbol)
                                        updated = True
                                elif next_symbol in grammar:
                                    next_symbol_first_set = first_sets[next_symbol]
                                    new_follow = next_symbol_first_set - {'ε'}
                                    if not new_follow.issubset(follow_sets[symbol]):
                                        follow_sets[symbol].update(new_follow)
                                        updated = True
                                    
                                    # Case 2: If FIRST(β) contains ε, add FOLLOW(A) to FOLLOW(B)
                                    if 'ε' in next_symbol_first_set and not follow_sets[non_terminal].issubset(follow_sets[symbol]):
                                        follow_sets[symbol].update(follow_sets[non_terminal])
                                        updated = True
                            # Case 3: If A -> αB or A -> αBβ where β -> ε, add FOLLOW(A) to FOLLOW(B)
                            elif i == len(production) - 1:
                                if not follow_sets[non_terminal].issubset(follow_sets[symbol]):
                                    follow_sets[symbol].update(follow_sets[non_terminal])
                                    updated = True
            
            if not updated:
                break

    # First, compute the FIRST sets
    for non_terminal in grammar:
        compute_first(non_terminal)

    # Then, compute the FOLLOW sets
    compute_follow()

    # Return both FIRST and FOLLOW sets as a tuple
    return first_sets, follow_sets

def generate_parsing_table(grammar, terminals):
    # Step 1: Compute FIRST and FOLLOW sets using the imported function
    first_sets, follow_sets = compute_first_and_follow(grammar, terminals)

    # Step 2: Initialize the parsing table (Non-terminals as rows, Terminals as columns)
    non_terminals = list(grammar.keys())
    parsing_table = pd.DataFrame(index=non_terminals, columns=list(terminals), dtype=str)

    # Step 3: Fill the parsing table using FIRST and FOLLOW sets
    for non_terminal, productions in grammar.items():
        for production in productions:
            first_set = set()  # Will store the FIRST set for this specific production

            # Calculate the FIRST set for this production
            for symbol in production:
                if symbol in terminals:  # If it's a terminal
                    first_set.add(symbol)
                    break
                elif symbol in grammar:  # If it's a non-terminal
                    first_set.update(first_sets[symbol] - {'ε'})
                    if 'ε' not in first_sets[symbol]:  # Stop if ε is not in the FIRST set
                        break
            else:
                first_set.add('ε')  # Add ε if the production can derive ε

            # For each terminal in the FIRST set, add the production to the parsing table
            for terminal in first_set:
                if terminal != 'ε':
                    parsing_table.loc[non_terminal, terminal] = f"{non_terminal} -> {' '.join(production)}"

            # If ε is in the FIRST set, place the production in the FOLLOW set of the non-terminal
            if 'ε' in first_set:
                for terminal in follow_sets[non_terminal]:
                    parsing_table.loc[non_terminal, terminal] = f"{non_terminal} -> ε"

    return parsing_table



"""

Format for grammar:
    {                                                                  
                                                                      | - here split the grammar into 2 different lists if there is | (or)
                                                                      |     for example: E->+TE' | epsilon results in "E'": [['+', 'T', "E'"], ['ε']].
                                                                      |
        '[variable name]': [['terminal/variable', 'terminal/variable'], ['terminal/variable', 'terminal/variable]],
        ...
    }


"""



# Example grammar and terminals 
# example from the lecture
grammar = {
    'E': [['T', "E'"]],
    "E'": [['+', 'T', "E'"], ['ε']],
    'T': [['F', "T'"]],
    "T'": [['*', 'F', "T'"], ['ε']],
    'F': [['(', 'E', ')'], ['id']]
}

terminals = {'+', '*', 'id', '(', ')', '$'}

# Generate and display the parsing table
parsing_table = generate_parsing_table(grammar, terminals)

print("Parsing Table:")
print(parsing_table)




# example from the tutorial
grammar2 = {
    'BE': [['BT', "BE'"]],
    "BE'": [['∨', 'BT', "BE'"], ['ε']],
    'BT': [['BF', "BT'"]],
    "BT'": [['∧', 'BF', "BT'"], ['ε']],
    'BF': [['¬', 'BF'], ['(', 'BE', ')'], ['true'], ['false'], ['RE']],
    'RE': [['RF', "RE'"]],
    "RE'": [['<', 'RF'], ['>', 'RF'], ['=', 'RF']],
    'RF': [['var'], ['num']]
}

terminals2 = {'∨', '∧', '¬', '(', ')', 'true', 'false', '<', '>', '=', 'var', 'num'}


parsing_table2 = generate_parsing_table(grammar2, terminals2)

print("Parsing Table:")
print(parsing_table2)

# assignment parsing table
assignmentGrammar = {
    'prog': [['public', 'class', 'ID', '{', 'public', 'static', 'void', 'main', '(', 'String[]', 'args', ')', '{', 'los', '}', '}']],
    'los': [['stat', 'los'], ['ε']],
    'stat': [['while'], ['for'], ['if'], ['assign', ';'], ['decl', ';'], ['print', ';'], [';']],
    'while': [['while', '(', 'rel expr', 'bool expr', ')', '{', 'los', '}']],
    'for': [['for', '(', 'for start', ';', 'rel expr', 'bool expr', ';', 'for arith', ')', '{', 'los', '}']],
    'for start': [['decl'], ['assign'], ['ε']],
    'for arith': [['arith expr'], ['ε']],
    'if': [['if', '(', 'rel expr', 'bool expr', ')', '{', 'los', '}', 'else if']],
    'else if': [['else?if', '{', 'los', '}', 'else if'], ['ε']],
    'else?if': [['else', 'poss if']],
    'poss if': [['if', '(', 'rel expr', 'bool expr', ')'], ['ε']],
    'assign': [['ID', '=', 'expr']],
    'decl': [['type', 'ID', 'poss assign']],
    'poss assign': [['=', 'expr'], ['ε']],
    'print': [['System.out.println', '(', 'print expr', ')']],
    'type': [['int'], ['boolean'], ['char']],
    'expr': [['rel expr', 'bool expr'], ['char expr']],
    'char expr': [["'", 'char', "'"]],
    'bool expr': [['bool op', 'rel expr', 'bool expr'], ['ε']],
    'bool op': [['bool eq'], ['bool log']],
    'bool eq': [['=='], ['!=']],
    'bool log': [['&&'], ['||']],
    'rel expr': [['arith expr', "rel expr'"], ['true'], ['false']],
    "rel expr'": [['rel op', 'arith expr'], ['ε']],
    'rel op': [['<'], ['<='], ['>'], ['>=']],
    'arith expr': [['term', "arith expr'"]],
    "arith expr'": [['+', 'term', "arith expr'"], ['-', 'term', "arith expr'"], ['ε']],
    'term': [['factor', "term'"]],
    "term'": [['*', 'factor', "term'"], ['/', 'factor', "term'"], ['%', 'factor', "term'"], ['ε']],
    'factor': [['(', 'arith expr', ')'], ['ID'], ['num']],
    'print expr': [['rel expr', 'bool expr'], ['"', 'string lit', '"']],
}

assignmentTerminals = {'+', '-', '*', '/', '%', '=', '==', '!=', '<', '>', '<=', '>=', '(', ')', '{', '}', '&&', '||', ';', 'num', 'ID', 'true', 'false', 'public', 'class', 'static', 'void', 'main', 'String[]', 'args', 'int', 'boolean', 'char', 'System.out.println', 'while', 'for', 'if', 'else', '"', "'", '$',}


assignment_parsing_table = generate_parsing_table(assignmentGrammar, assignmentTerminals)

print(assignment_parsing_table.columns)

def save_parsing_table_to_excel(parsing_table, filename):
    parsing_table.to_excel(filename)

print("Parsing Table:")
print(assignment_parsing_table)

save_parsing_table_to_excel(assignment_parsing_table, "ll1parsing_table.xlsx")
