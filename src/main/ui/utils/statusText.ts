import { IProductState } from "@/interfaces/parte-y-reparte-interfaces";

export const statusText = (state : IProductState) : string => {

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