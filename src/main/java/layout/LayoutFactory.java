package layout;

import database.UserType;

public interface LayoutFactory {
    String getLayout(UserType userType);
}
