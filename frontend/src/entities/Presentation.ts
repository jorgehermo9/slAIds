import { z } from "zod";

export const PresentationSchema = z
  .object({
    id: z.number(),
    title: z.string(),
    description: z.string(),
    image: z.string(),
    createdAt: z.string(),
    updatedAt: z.string(),
  })
  .strict();

type Presentation = z.infer<typeof PresentationSchema>;
export default Presentation;
