import { z } from "zod";

export const SlideSchema = z
  .object({
    id: z.number(),
    title: z.string(),
    description: z.string(),
    image: z.string(),
    createdAt: z.string(),
    updatedAt: z.string(),
  })
  .strict();

type Slide = z.infer<typeof SlideSchema>;
export default Slide;
