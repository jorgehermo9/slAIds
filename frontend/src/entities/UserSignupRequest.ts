import { z } from "zod";

export const UserSignupRequest = z
  .object({
    userName: z.string(),
    password: z.string(),
    email: z.string(),
  })
  .strict();

type User = z.infer<typeof UserSignupRequest>;
export default User;