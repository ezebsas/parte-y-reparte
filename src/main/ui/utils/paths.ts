export const parteYRepartePaths = {
  products: {
    base: "/products",
    details: (id: string) => `/products/${id}`,
    edit: (id: string) => `/products/${id}`,
    close: (id: string) => `/products/${id}/close`,
    subscription: {
      base: (id : string) => `/products/${id}/subscription` 
    }
  },
  stats: {
    uniqueUsers: "/stats/unique-users",
    publicationAmount : "/stats/publications",
  },
  me: {
    base: "/users/me",
    products: "/users/me/products",
    suscriptions: {
      base: "/users/me/subscriptions",
      details: (id: string) => `/users/me/subscriptions/${id}`
    },
    notifications: "/users/me/notifications"
  },
  register: "/auth/register"
}