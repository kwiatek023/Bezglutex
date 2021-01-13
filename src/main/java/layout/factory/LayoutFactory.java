package layout.factory;

import database.UserType;

public interface LayoutFactory {
    String getLayout(UserType userType);
}
