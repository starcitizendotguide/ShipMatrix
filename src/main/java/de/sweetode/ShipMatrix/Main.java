package de.sweetode.ShipMatrix;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        //--- Fetch New Data
        ShipMatrix matrix = new ShipMatrix();
        matrix.execute();

        //--- Compare
        CompareMatrix compareMatrix = new CompareMatrix();

        //--- Serialize
        SerializeData serializeData = new SerializeData(compareMatrix.diffAll());

    }

}
