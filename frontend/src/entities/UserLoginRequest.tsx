import { z } from "zod";

export const UserLoginRequest = z
  .object({
    userName: z.string(),
    password: z.string(),
  })
  .strict();

type User = z.infer<typeof UserLoginRequest>;
export default User;
