import { ProductState } from "@/enums/ProductState";

export const statusText = (state : ProductState) : string => {

  let text = "";

  switch(state) {
    case "OPEN":
      text = "🟢 Active";
      break;
    case "CLOSED_COMPLETED":
      text = "🔵 Completed";
      break;
    case "CLOSED_INCOMPLETE":
      text = "🔴 Incomplete";
      break;
    case "CANNOT_BE_DISTRIBUTED":
      text = "⚫ Cannot be distributed";
      break;
    default:
      text = "🟠 Planning";
      break
  }

  return text;
}