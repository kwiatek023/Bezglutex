package layout.factory;

import database.UserType;

public class ConcreteLayoutFactory implements LayoutFactory {
    @Override
    public String getLayout(UserType userType) {
       switch (userType) {
            case salesman: {
                return "salesmanView";
            }
           case store_keeper:
           case store_manager: {
               return "storeKeeperView";
           }
           case admin: {
               return "adminView";
           }
           default: {
               return null;
           }
        }
    }
}
