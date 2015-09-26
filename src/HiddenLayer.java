 import java.util.*;

public class HiddenLayer extends Layer
{    
    /**
     * Constant representing numerical value of -1, used for computing the output of neurons
     */
    public static final int MINUSONE = -1;
    
    protected Layer previousLayer;//the layer preceding this layer
    protected double[][] weights; //weights between this layer and previous layer
    
    public HiddenLayer(int count,Layer previousLayer,String activationFunction)
    {
        super(count);
        this.previousLayer = previousLayer;
        initializeNeurons();
        weights = new double[this.count][previousLayer.count];
        initializeWeights();
        initializeWeightedSums();
        setOutputs(activationFunction);
    }
    
    //for use in the output layer class
    public HiddenLayer(int count,Layer previousLayer)
    {
        super(count);
        this.previousLayer = previousLayer;
        initializeNeurons();
        weights = new double[this.count][previousLayer.count];
        initializeWeights();
        initializeWeightedSums();
    }
    
    public void initializeNeurons()//initialize all neurons to default values
    {
       for(int i = 0;i < count;i++)
            layer[i] = new Neuron(0,1);//default values of weightedSum=0 and output=1
    }
    
    public void initializeWeights()
    {
         for(int i = 0;i < count;i++)
            for(int j = 0;j < previousLayer.count;j++)
                weights[i][j] = (Math.random())-0.5;//default values in the range [-0.5,0.5)
    }
    
    public void setWeights(double[][] weights)
    {
        this.weights = weights;
    }
    
    /**
     * Initializes the weighted sum of all neurons in this hidden layer. 
     * The weigted sum of each neuron is the sum of the products of each link weight*output(z) of the neuron link originate from
     */
    public void initializeWeightedSums()
    {
        double sum = 0;
        for(int i = 0;i < count;i++)//auxiliary nuron's weightedSum is already set to 0 by initializeNeurons() method
        {
            for(int j = 0;j < previousLayer.count;j++)
                sum = sum + (weights[i][j]) * previousLayer.layer[j].getOutput();//weightedSum = sum of all the (weight of link from prevoius layer*output of the neuron link comes from)
            layer[i].setWeightedSum(sum);
            sum = 0;
        }
    }
    
    /**
     * Computes the outputs of neurons in this hideen layer using Sigmoid activation function
     * The output of each neuron is computed as the inverse of 1+(e^(-weightedSum))
     */
    public void setOutputs(String activationFunction)//set the z values
    {
        double temp = 0, negTemp = 0;
        double negativeSum = 0;//holds the negative value of weightedSum
        double sum = 0, numerator = 0, denominator = 0;
        if(activationFunction.equals("sigmoid"))
        {
            for(int i = 1;i < count;i++)//auxiliary nuron's output is already set to 1 by initializeNeurons() method
            {
                negativeSum = layer[i].getWeightedSum()*MINUSONE;//negate the weightedSum of neuron
                temp = Math.pow(Math.E,negativeSum);
                temp = 1 / (1 + temp);
                layer[i].setOutput(temp);//set the output of neuron
                temp = negativeSum = 0;
            }
        }
        else if(activationFunction.equals("arctangent"))
        {
            for(int i = 1;i < count;i++)//auxiliary nuron's output is already set to 1 by initializeNeurons() method
            {
                temp = 2.0 / Math.PI;
                temp = temp * Math.atan(layer[i].getWeightedSum());
                layer[i].setOutput(temp);//set the output of neuron
                temp = 0;
            }
        }
        else if(activationFunction.equals("hyperbolic tangent"))
        {
            for(int i = 1;i < count;i++)//auxiliary nuron's output is already set to 1 by initializeNeurons() method
            {
                sum = layer[i].getWeightedSum();
                negativeSum = sum * MINUSONE;//negate the weightedSum of neuron
                temp = Math.pow(Math.E,sum);
                negTemp = Math.pow(Math.E,negativeSum);
                numerator = temp - negTemp;
                denominator = temp + negTemp;
                layer[i].setOutput(numerator / denominator);//set the output of neuron
                sum = negativeSum = temp = negTemp = numerator = denominator = 0;
            }
        }
        else
        {
            System.out.println("The requested activation function is not supported.");
            System.out.println("Please check the spelling or use one of sigmoid, arctangent or hyberbolic tangent functions.");
            System.exit(0);
        }
    }
    
    //for use during the training
    public void updateOutputs(String activationFunction)
    {
         initializeWeightedSums();//both iniitializes and updates the weights
         setOutputs(activationFunction);
    }
}
