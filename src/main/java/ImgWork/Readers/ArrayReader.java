package ImgWork.Readers;

import ImgWork.Converter.ImageArrayConverter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayReader {

    public static int[][] readTo2d(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        ArrayList<List<Integer>> result = new ArrayList<>();
        reader.lines().forEach(line ->
                result.add(Stream.of(line.split("\\t")).map(Integer::valueOf).collect(Collectors.toList()))
        );
        int[][] arrayResult  = new int[result.size()][result.size()];
        for(int i =0;i<arrayResult.length;i++ ){
            for (int j = 0;j<arrayResult.length;j++){
                arrayResult[i][j] = result.get(i).get(j);
            }
        }
        return arrayResult;
    }

    public static int[] readToArray(String filepath){
        return ImageArrayConverter.imageToArray(ImgReader.readImage(filepath));
    }
}
