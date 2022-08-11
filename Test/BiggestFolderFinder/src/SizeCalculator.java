import java.io.File;
import java.util.HashMap;

public class SizeCalculator {

    private final static char[] sizeMultipiers = {'B', 'K','M', 'G', 'T'};
    private static HashMap<Character, Integer> char2multiplier = getMultipliers();


    //Байты в Кб, Мб, Гб
    public static String getHumanReadableSize(long size){
        for(int i = 0; i < sizeMultipiers.length; i++){
            double value = ((double) size) / Math.pow(1024, i);
            if(value < 1024) {
                return Math.round(value * 100)/100. //для двух знаков после запятой
                        + "" + sizeMultipiers[i] + (i>0 ? "b" : "");
            }
        }
        return "Very big!";
    }

    //B, Kb, Mb, Gb,Tb в байты
    public static long getSizeFromHumanReadable(String size){
        char sizeFactor = size.replaceAll("[0-9\\s+]+","").charAt(0);
        int multiplier = char2multiplier.get(sizeFactor);
        return multiplier * Long.parseLong(size.replaceAll("[^0-9]",""));
    }

    //Словарь для getSizeFromHumanReadable
    private static HashMap<Character, Integer> getMultipliers(){
        HashMap<Character, Integer> char2multiplier = new HashMap<>();
        for(int i = 0; i < sizeMultipiers.length; i++){
            char2multiplier.put(sizeMultipiers[i], (int) Math.pow(1024,i));
        } return char2multiplier;
    }
}
