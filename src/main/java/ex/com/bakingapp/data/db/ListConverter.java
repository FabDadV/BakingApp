package ex.com.bakingapp.data.db;

import java.util.List;
import java.util.Arrays;
import android.arch.persistence.room.TypeConverter;

public class ListConverter {
    @TypeConverter
    public String fromList(List<String> stringList) {
        StringBuilder data = new StringBuilder();
        for (String str : stringList) {data.append(str).append("\n");}
//                stringList.stream().collect(Collectors.joining("\n"));
        return data.toString();
    }
    @TypeConverter
    public List<String> toList(String data) {
        return Arrays.asList(data.split("\n"));
    }
}
