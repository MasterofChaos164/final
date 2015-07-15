package neuralnetwork;

/************************************************************************
 * \brief: a single Neuron class * * (c) copyright by J�rn Fischer * * *
 * 
 * @autor: Prof.Dr.J�rn Fischer *
 * @email: j.fischer@hs-mannheim.de * *
 * @file : Neuron.java *
 *************************************************************************/

public class Neuron {
    public double output;
    public double weight[];

    /**
     * @brief: setter function to set the number of weights
     * @param numOfWeights
     */
    public void setNumWeights(int numWeights) {
		weight = new double[numWeights];
    }
}