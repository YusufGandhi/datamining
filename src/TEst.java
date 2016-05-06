
public class TEst implements Cloneable {
	String name;
	public static void main(String [] args) {
		TEst t1 = new TEst();
		t1.name = "Yes";
		TEst t2 = new TEst(t1);
		t2.name = "No";
		System.out.println(t1.name);
		System.out.println(t2.name);
			
	}
	
	public TEst() {}
	public TEst(TEst t) {
		this.name = t.name;
	}
}
