import { ProductState } from "../enums/ProductState";
import { ProductUnit } from "../enums/ProductUnit";
import { IUser } from "./parte-y-reparte-interfaces";

export interface IProduct {
  id: string;
  name: string;
  image: string;
  link: string;
  deadline: string;
  maxPeople: number;
  minPeople: number;
  totalCost: number;
  state: ProductState;
  quantity: number;
  unit: ProductUnit;
  subscribers: IUser[];
  owner: IUser;
}