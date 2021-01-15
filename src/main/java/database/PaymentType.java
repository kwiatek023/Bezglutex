package database;

import database.exceptions.MismatchedPaymentTypeException;

import java.util.ArrayList;

public enum PaymentType {
  bank_transfer("Bank transfer", "bank_transfer"), on_delivery("On delivery", "on_delivery");

  private final String UILabel;
  private String DBLabel;

  PaymentType(String UILabel, String DBLabel) {
    this.UILabel = UILabel;
    this.DBLabel = DBLabel;
  }

  public static ArrayList<String> valuesToUILabel() {
    ArrayList<String> valuesToUILabel = new ArrayList<>();

    for(PaymentType paymentType: PaymentType.values()) {
      valuesToUILabel.add(paymentType.toUILabel());
    }

    return valuesToUILabel;
  }

  private String toUILabel() {
    return UILabel;
  }

  public static String convertUILabelToDBLabel(String UILabel) throws MismatchedPaymentTypeException {
    switch (UILabel) {
      case "On delivery": {
        return "on_delivery";
      }
      case "Bank transfer": {
        return "bank_transfer";
      }
      default: {
        throw new MismatchedPaymentTypeException();
      }
    }
  }
}
