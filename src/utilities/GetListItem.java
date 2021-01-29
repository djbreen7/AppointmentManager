package utilities;

import java.util.List;

public interface GetListItem<T> {
    T getCountryByName(List<T> list, String str);
}
