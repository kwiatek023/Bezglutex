package layout.factory;

import database.UserType;

public class ConcreteLayoutFactory implements LayoutFactory {
    @Override
    public String getLayout(UserType userType) {
       switch (userType) {
            case salesman: {
                return "salesmanView";
            }
           case store_keeper: {
               return "storeKeeperView";
           }
           case store_manager: {
               return "storeManagerView";
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
