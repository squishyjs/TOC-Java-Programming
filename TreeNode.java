import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

class TreeNode {

	public enum Label implements Symbol {
		prog, los, stat, whilestat, forstat, forstart, forarith, ifstat, elseifstat, elseorelseif, possif, assign, decl,
		possassign, print, type, expr, boolexpr, boolop, booleq, boollog, relexpr, relexprprime, relop, arithexpr,
		arithexprprime, term, termprime, factor, printexpr, charexpr, epsilon, terminal;

		@Override
		public boolean isVariable() {
			return true;
		}

	};

	private Label label;
	private Optional<Token> token;
	private TreeNode parent;
	private List<TreeNode> children;

	public TreeNode(Label label, TreeNode parent) {
		this.label = label;
		this.token = Optional.empty();
		this.parent = parent;
		children = new ArrayList<TreeNode>();
	}

	public TreeNode(Label label, Token token, TreeNode parent) {
		this.label = label;
		this.token = Optional.of(token);
		this.parent = parent;
		children = new ArrayList<TreeNode>();
	}

	public void setToken(Token token) {
		this.token = Optional.of(token);
	}

	public void addChild(TreeNode child) {
		children.add(child);
	}

	public Optional<Token> getToken() {
		return this.token;
	}

	public TreeNode getParent() {
		return this.parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public List<TreeNode> getChildren() {
		return this.children;
	}

	public Label getLabel() {
		return this.label;
	}

	@Override
	public String toString() {
		return "[" + this.label + ", " + this.token + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.label, this.token, this.parent);
	}

}
