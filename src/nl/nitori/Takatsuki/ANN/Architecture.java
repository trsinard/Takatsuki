package nl.nitori.Takatsuki.ANN;

public class Architecture implements Comparable<Architecture> {

	private String file;
	private double alpha;
	private double tolerance;
	private int hidden_layers;
	private int[] hidden_nodes;
	private int k_bins;
	private NeuralNetwork nNetwork;
	private Examples examples;
	private double errors;

	public Architecture(String file, Examples examples, double alpha,
			double tolerance, int hidden_layers, int[] hidden_nodes) {
		this.file = file;
		this.k_bins = examples.getBinCount();
		this.alpha = alpha;
		this.tolerance = tolerance;
		this.hidden_layers = hidden_layers;
		this.hidden_nodes = hidden_nodes.clone();
		this.errors = 0;
		this.nNetwork = null;
		this.examples = examples;
		calculateArchitecture();
	}

	private void calculateArchitecture() {
		
		for(int i = 0; i < k_bins; i++){
			int[] top = getTopology();
			nNetwork = new NeuralNetwork(top);	
			nNetwork.trainOnExamples(examples.getTrainingBin(i), examples.getScale(), tolerance, alpha);
			for(Example ex : examples.getValidationBin(i)){
				nNetwork.computeActivation(ex.getInput());
				errors+=ex.computeError(nNetwork.getActivation(examples.getScale()));
			}
			
		}
	}

	private int[] getTopology() {
		int[] top = null;
		top = new int[hidden_layers + 2];
        top[0] = examples.getInputCount();
        top[hidden_layers + 1] = examples.getOutputCount();
        top[1] = hidden_nodes[0];
        if(hidden_layers == 1){
        	return top;
        } 
        top[2] = hidden_nodes[1];
        return top;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	public int getHidden_layers() {
		return hidden_layers;
	}

	public void setHidden_layers(int hidden_layers) {
		this.hidden_layers = hidden_layers;
	}

	public int[] getHiddenNodes() {
		return hidden_nodes;
	}

	public void setHiddennodes(int[] hidden_nodes) {
		this.hidden_nodes = hidden_nodes;
	}

	public int getBins() {
		return k_bins;
	}

	public void setBins(int k_bins) {
		this.k_bins = k_bins;
	}

	public NeuralNetwork getnNetwork() {
		return nNetwork;
	}

	public void setnNetwork(NeuralNetwork nNetwork) {
		this.nNetwork = nNetwork;
	}

	public Examples getExamples() {
		return examples;
	}

	public void setExamples(Examples examples) {
		this.examples = examples;
	}

	public int compareTo(Architecture arch2) {
		if (this.getAverageError() > arch2.getAverageError())
			return 1;
		else if (this.getAverageError() < arch2.getAverageError())
			return -1;
		else
			return 0;
	}

	public double getAverageError() {
		return errors / k_bins;
	}
	
	public String toString(){
		String top = "";
		for(int a : getTopology()){
			top += a + " ";
		}
		return "Topology: " + top + "\nError: " + getAverageError();
	}
}
