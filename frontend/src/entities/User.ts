import { z } from "zod";

export const UserSchema = z
  .object({
    userName: z.string(),
    password: z.string(),
    email: z.string(),
  })
  .strict();

type User = z.infer<typeof UserSchema>;
export default User;