type JWTSessionUser = {
  name?: string | null | undefined;
  email?: string | null | undefined;
  image?: string | null | undefined;
  value?: {
    token?: string
  }
} | undefined