/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2024-06-04 01:41:49.

export interface IApiResponse<T> {
    message: string;
    value: T;
}

export interface IAuthResponse {
    token: string;
}

export interface IProduct {
    id: string;
    name: string;
    image: string;
    link: string;
    deadline: Date;
    maxPeople: number;
    minPeople: number;
    totalCost: number;
    state: IProductState;
    unit: IProductUnit;
    quantity: number;
    subscribers: IUser[];
    owner: IUser;
}

export interface IUser {
    name: string;
    age: number;
    email: string;
    id: string;
}

export interface INotification {
    title: string;
    message: string;
    date: string;
    productId: string;
}

export type IProductState = "OPEN" | "CLOSED_COMPLETED" | "CANNOT_BE_DISTRIBUTED" | "CLOSED_INCOMPLETE";

export type IProductUnit = "KILOGRAM" | "GRAM" | "LITER" | "MILLILITER" | "UNIT";
