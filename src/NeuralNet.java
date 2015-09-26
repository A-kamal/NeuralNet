import java.util.*;
/**
 * The class NeuralNet consists of an input layer, an output layer and the desired number of hidden layers.
 * Hidden layers are kept in an array of HiddenLayers. Each HiddenLayer and the output layer has a previous Layer from which it gets the stimuli.
 * Each layer consists of an array of Neurons.
 */
public class NeuralNet
{
    /** Number of hidden layers in this NeuralNet */
    protected int hiddenLayerCount;
    /** Hidden layers of this NeuralNet are in an array of HiddenLayer */
    protected HiddenLayer[] hiddenLayers;
    /**Input layer of this NeuralNet */
    protected InputLayer inputLayer;
    /**Output layer of this NeuralNet */
    protected OutputLayer outputLayer;
    private ArrayList<double[][]> networkWeights;
    private String activationFunction;

    /**
     * Constructs a new NeuralNet having the passed number of hidden layers and the passed number of neurons in each hidden layer.
     * The neurons of the input layer of this NeuralNet have the outputs passed in the array inputs
     * @param hiddenLayerCount The desired number of hidden layers
     * @param neuronCount The desired number of neurons in each hidden layer
     * @param inputs The inputs to this NeuralNet
     */
    public NeuralNet(int hiddenLayerCount,int outputCount,double[] inputs,int[] hiddenLayersNeuronsCount,String activationFunction)
    {
        this.hiddenLayerCount = hiddenLayerCount;
        this.activationFunction = activationFunction;
        this.inputLayer = new InputLayer(inputs);//create the input layer, inputs array will be passed from gui to the constructor
        createHiddenLayers(hiddenLayerCount,hiddenLayersNeuronsCount);
        createOutputLayer(outputCount);//Create the Output Layer
        fillNetworkWeights();
    }
    
   /**
     * This constructor is for use during the training to feed the output values to the NeuralNet as well as the input values.
     * Constructs a new NeuralNet having the passed number of hidden layers,the passed number of neurons in each hidden layer.
     * The neurons of the input layer of this NeuralNet have the outputs passed in the array inputs.
     * The neurons of the output layer of this NeuralNet have the outputs passed in the array outputs.
     * @param hiddenLayerCount The desired number of hidden layers
     * @param neuronCount The desired number of neurons in each hidden layer
     * @param inputs The inputs to the neural network
     */
    /*public NeuralNet(int hiddenLayerCount,int neuronCount,double[] inputs,double[] outputs,int[] hiddenLayersNeuronsCount)
    {
        this(hiddenLayerCount,neuronCount,outputs.length,inputs,hiddenLayersNeuronsCount);
        outputLayer.setOutputsForTesting(outputs);//populate the Output Layer by the values read from GUI        
    }*/
    
     /**
     *  Returns the outputs of this NeuralNet (outputs of the OutputLayer of this NeuralNet)
     *  @return Array of double contatinig the outputs of this NeuralNet
     */
    public double[] getOutputs()
    {
       return outputLayer.getOutputs();
    }
    
    public void setLayerWeights(int layerNumber,double[][] weights)//set the weights for the specified layer number
    {
        if(layerNumber == (hiddenLayerCount))//if output layer
            outputLayer.setWeights(weights);
        else
            hiddenLayers[layerNumber].setWeights(weights);
        networkWeights.set(layerNumber,weights);
    }
    
    public void setNetworkWeights(ArrayList<double[][]> networkWeights)//set all the weights of the neural network
    {
        if(this.networkWeights.size() != networkWeights.size())
        {
            System.out.println("Sizes do not match");
            System.exit(0);
        }
        for(int i=0;i < this.networkWeights.size();i++)
            this.networkWeights.set(i,networkWeights.get(i));
       
    }
    
    public ArrayList<double[][]> getNetworkWeights()
    {
        return networkWeights;
    }
    
    //used after weights are updated by the training class
    public void updateNeuralNetwork(double[] SampleInputs)
    {
        inputLayer.updateInputValues(SampleInputs);
        for(int i = 0;i < hiddenLayerCount;i++)
            hiddenLayers[i].updateOutputs(activationFunction);
        outputLayer.updateOutputs();
    }
    
    //For use just inside the class, to make the constructor clean
    private void createHiddenLayers(int hiddenLayerCount,int[] hiddenLayersNeuronsCount)
    {
        hiddenLayers = new HiddenLayer[hiddenLayerCount]; 
        if(hiddenLayerCount != hiddenLayersNeuronsCount.length)
        {
            System.out.println("Number of hidden layers do not match the number of neurons for each hidden layer");
            System.exit(0);
        }
        hiddenLayers[0] = new HiddenLayer(hiddenLayersNeuronsCount[0],inputLayer,activationFunction);//first hidden layer, its previous layer is input layer
        for(int i = 1;i < hiddenLayerCount;i++)//initialize hidden layers only,exclude output layer at this point
            hiddenLayers[i] = new HiddenLayer(hiddenLayersNeuronsCount[i],hiddenLayers[i-1],activationFunction);
    }
    
    //For use just inside the class, to make the constructor clean
    private void createOutputLayer(int outputCount)
    {
       int lastHiddenLayer = hiddenLayerCount-1;
       this.outputLayer = new OutputLayer(outputCount,hiddenLayers[lastHiddenLayer]);//create the output layer
    }
    
    private void fillNetworkWeights()
    {
        this.networkWeights = new ArrayList<double[][]>(hiddenLayerCount+1);//account for the output layer too
        for(int i = 0;i < hiddenLayerCount;i++)
            networkWeights.add(hiddenLayers[i].weights);
        networkWeights.add(outputLayer.weights);
    }
    
    //************************************************************************************************************************************************************
    //local gradients, eqns. 3.28 and 3.29
    //each index of Arraylist is all the local gradients for one layer
    private ArrayList<double[]> localGradientsPerSample(double[] desiredOutputs,double[] desiredInputs)//parameter is outputs of one sample
    {
        updateNeuralNetwork(desiredInputs);
        ArrayList<double[]> networkGradients = new ArrayList<double[]>(hiddenLayerCount+1);//plus one to account for the output layer
        //-------------------------------------------
        //gradients at the output layer
        double[] NNOutputs = getOutputs();
        if(NNOutputs.length != desiredOutputs.length)
        {
            System.out.println("Number of desired outputs do not match the number of NN outputs.");
            System.exit(0);
        }
        double[] gradients = new double[outputLayer.count];
        for(int i = 0;i < NNOutputs.length;i++)
            gradients[i] = NNOutputs[i] - desiredOutputs[i];
        networkGradients.add(gradients);
        //-------------------------------------------
        //gradients at the hidden layers
        HiddenLayer upperLayer = outputLayer;
        HiddenLayer thisLayer = null;
        double[] upperLayerGradients = gradients;
        double product = 0;
        for(int i = hiddenLayerCount-1;i >= 0;i--)//index counter for hidden layers
        {
            gradients = new double[hiddenLayers[i].count];//gradients of this layer
            thisLayer = hiddenLayers[i];
            for(int j = 0;j < thisLayer.layer.length;j++)//index counter for number of neurons in this layer
            {
                for(int k = 0;k < upperLayer.count;k++)
                {
                    product = product + (upperLayerGradients[k] * upperLayer.weights[k][j]);
                }
                product = product * thisLayer.layer[j].getOutput() * (1-thisLayer.layer[j].getOutput());
                gradients[j] = product;
                product = 0;
            }
            networkGradients.add(gradients);
            upperLayer = hiddenLayers[i];
            upperLayerGradients = gradients;
        }
        return networkGradients;
    }
    
    //The first index of ArrayList is the errors of the output layer, second index is errors of the last hidden layer,...
    public ArrayList<double[][]> errorToWeightDerivativesPerSample(double[] desiredOutputs,double[] desiredInputs)
    {
        ArrayList<double[]> networkGradients = localGradientsPerSample(desiredOutputs,desiredInputs);
        ArrayList<double[][]> networkErrorToWeightDerivatives = new ArrayList<double[][]>(hiddenLayerCount+1);
        double[][] errorToWeightDerivatives = null;
        int m = 0;//index counter for networkGradients ArrayList
        double[] thisLayerGradients = null;
        HiddenLayer thisLayer = outputLayer;
        Layer previousLayer = null;
        for(int i = hiddenLayerCount-1;i >= 0;i--)//index counter for previous layer
        {
            previousLayer = hiddenLayers[i];
            errorToWeightDerivatives = new double[thisLayer.count][previousLayer.count];
            thisLayerGradients = networkGradients.get(m);
            for(int j = 0;j < thisLayerGradients.length;j++)
            {
                for(int k = 0;k < previousLayer.count;k++)
                {
                    errorToWeightDerivatives[j][k] = thisLayerGradients[j]*previousLayer.layer[k].getOutput();
                }
            }
            networkErrorToWeightDerivatives.add(errorToWeightDerivatives);
            thisLayer = hiddenLayers[i];
            m = m + 1;
        }
        //--------------For the case of first hidden layer
        previousLayer = inputLayer;
        errorToWeightDerivatives = new double[thisLayer.count][previousLayer.count];
        thisLayerGradients = networkGradients.get(m);
        for(int j = 0;j < thisLayerGradients.length;j++)
        {
            for(int k = 0;k < previousLayer.count;k++)
            {
                errorToWeightDerivatives[j][k] = thisLayerGradients[j]*previousLayer.layer[k].getOutput();
            }
        }
        networkErrorToWeightDerivatives.add(errorToWeightDerivatives);
        
        return networkErrorToWeightDerivatives;
    }
    
     //The first index of ArrayList is the errors of the first hidden layer, second index is errors of the second hidden layer,...
    public ArrayList<double[][]> errorPerSample(double[] desiredOutputs,double[] desiredInputs)
    {
        ArrayList<double[][]> errorsBackwards = errorToWeightDerivativesPerSample(desiredOutputs,desiredInputs);
        ArrayList<double[][]> errors = new ArrayList<double[][]>(errorsBackwards.size());
        for(int i = errorsBackwards.size()-1;i >= 0;i--)
        {
            errors.add(errorsBackwards.get(i));
        }
        return errors;
    }
    
    //************************************************************************************
    //For use in Training
    //returns the error for all samples. Errors are listed starting from the first hidden layer, then the second hidden layer,... all the way up to the output layer
    public ArrayList<double[][][]> getError(double[][] outputSamples,double[][] inputSamples)
    {
        ArrayList<double[][][]> errors = new ArrayList<double[][][]>(inputSamples.length);
        ArrayList<double[][]> thisSampleErrors = null;
        double[][][] threeDimensionalError = null;
        if(outputSamples.length != inputSamples.length)
        {
            System.out.println("Input sample counts do not match the output sample count");
            System.exit(0);
        }
        for(int i = 0;i < outputSamples.length;i++)
        {
            thisSampleErrors = errorPerSample(outputSamples[i],inputSamples[i]);//get the error of the first sample
            threeDimensionalError = new double[thisSampleErrors.size()][][];
            for(int j = 0;j < thisSampleErrors.size();j++)
            {
                threeDimensionalError[j] = thisSampleErrors.get(j);
            }
            errors.add(threeDimensionalError);
        }
        return errors;
    }
}
