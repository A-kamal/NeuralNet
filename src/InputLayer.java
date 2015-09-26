public class InputLayer extends Layer
{
    
    public InputLayer(double[] inputValues) //input values are passed in an array
    {
        super(inputValues.length);
        initializeNeurons(inputValues);
    }
    
    public void initializeNeurons(double[] inputValues)
    {
        layer[0] = new Neuron(0,1);//auxiliary neuron
        for(int i = 1;i < count;i++) //count has protected access 
            layer[i] = new Neuron(0,inputValues[i-1]); //weighted sum for the neurons of first layer is zero 
    }
    
    //For use during training
    public void updateInputValues(double[] inputValues)
    {
        for(int i = 0;i < inputValues.length;i++)
        {
            layer[i].setOutput(inputValues[i]);
        }
    }
}
