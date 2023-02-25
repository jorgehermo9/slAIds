import { z } from "zod";

export const UserLoginRequestSchema = z
  .object({
    userName: z.string(),
    password: z.string(),
  })
  .strict();

type UserLoginRequest = z.infer<typeof UserLoginRequestSchema>;
export default UserLoginRequest;
