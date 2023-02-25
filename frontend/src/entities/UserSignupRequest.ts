import { z } from "zod";

export const UserSignupRequestSchema = z
  .object({
    userName: z.string(),
    password: z.string(),
    email: z.string(),
  })
  .strict();

type UserSignupRequest = z.infer<typeof UserSignupRequestSchema>;
export default UserSignupRequest;
