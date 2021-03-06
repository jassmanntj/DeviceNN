package mobile;

import Jama.Matrix;

import java.io.Serializable;

/**
 * Created by jassmanntj on 4/13/2015.
 */
public class DevicePoolingLayer extends DeviceConvPoolLayer implements Serializable {
    private int poolDim;
    private int type;

    public DevicePoolingLayer(int poolDim, int type) {
        this.poolDim = poolDim;
        this.type = type;
    }


    public Matrix[] compute(Matrix[] in) {
        Matrix[] result = new Matrix[in.length];
        for(int i = 0; i < in.length; i++) {
            result[i] = pool(in[i]);
        }
        return result;
    }

    private Matrix pool(Matrix convolvedFeature) {
        int resultRows = convolvedFeature.getRowDimension()/poolDim;
        int resultCols = convolvedFeature.getColumnDimension()/poolDim;
        Matrix result = new Matrix(resultRows, resultCols);
        for(int poolRow = 0; poolRow < resultRows; poolRow++) {
            for(int poolCol = 0; poolCol < resultCols; poolCol++) {
                double max = 0;
                for(int i = 0; i < poolDim; i++) {
                    for(int j = 0; j < poolDim; j++) {
                        double val = convolvedFeature.get(poolRow*poolDim+i,poolCol*poolDim+j);
                        max = max > val? max:val;
                    }
                }
                result.set(poolRow, poolCol, max);
            }
        }
        return result;
    }
}
