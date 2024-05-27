import java.io.*; // Buffer ve File işlemleri
import java.util.*; // List,ArrayList,HashMap vb. yapılar bulunur
import java.util.stream.*; // Veri akışları üzerinde işlemler
import static java.util.stream.Collectors.toList; // doğrudan toList() kullanımı için

class Result {
    public static int uniqueWolfs(List<Integer> arr) {
        // Kurt türlerinin sayısını tutmak için hashMap
        Map<Integer, Integer> countMap = new HashMap<>();

        // Dizideki türlerin sayımı
        for (int wolf : arr) {
            countMap.put(wolf, countMap.getOrDefault(wolf, 0) + 1);
        }

        int maxCount = 0;
        int minId = Integer.MAX_VALUE;

        // countMap içinde döngü oluşturmak için
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            int id = entry.getKey();
            int count = entry.getValue();

            // Daha fazla tespit edilen bir tür bulduğumuzda veya aynı sayıda ama daha düşük ID'li bir tür bulduğumuzda
            if (count > maxCount || (count == maxCount && id < minId)) {
                maxCount = count;
                minId = id;
            }
        }

        return minId;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt"));

        int arrCount = Integer.parseInt(bufferedReader.readLine().trim());

        // arrCount'un min 5 olması
        if (arrCount < 5) {
            throw new IllegalArgumentException("arrCount en az 5 olmalıdır.");
        }

        String inputLine = bufferedReader.readLine().replaceAll("\\s+$", "");

        List<Integer> arr;

        // Girdinin boşluklarla ayrılmış mı yoksa bitişik mi olduğunu sorgulama
        if (inputLine.contains(" ")) {
            // Boşluklarla ayrılmışsa
            arr = Stream.of(inputLine.split("\\s+"))
                    .map(Integer::parseInt)
                    .collect(toList());
        } else {
            // Bitişikse
            arr = new ArrayList<>();
            for (char ch : inputLine.toCharArray()) {
                if (Character.isDigit(ch)) {
                    arr.add(Character.getNumericValue(ch));
                }
            }
        }

        // arrCount ve eleman sayısının karşılaştırılması
        if (arr.size() != arrCount) {
            throw new IllegalArgumentException("Girdi eleman sayısı, belirtilen arrCount ile uyuşmuyor.");
        }

        // her türün 1 <= x <= 5 aralığında olduğunu sorgulama
        for (int num : arr) {
            if (num < 1 || num > 5) {
                throw new IllegalArgumentException("Geçersiz giriş: Her tür 1, 2, 3, 4 veya 5 olmalıdır.");
            }
        }

        int result = Result.uniqueWolfs(arr);
        System.out.println(result);

        bufferedWriter.write(String.valueOf(result)); // Result'ı dosyaya yazdırır
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}