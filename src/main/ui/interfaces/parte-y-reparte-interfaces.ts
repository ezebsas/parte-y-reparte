/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2024-06-25 03:52:39.

export interface IApiResponse<T> {
    message: string;
    value: T;
}

/**
 * DTO for login response
 */
export interface IAuthResponse {
    token: string;
}

/**
 * DTO of a product
 */
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

/**
 * DTO of the user
 */
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
