import { z } from "zod";

export const SlideRequestDtoSchema = z
  .object({
    title: z.string(),
    prompt: z.string(),
    fontFamily: z.string(),
    primaryColor: z.string(),
    secondaryColor: z.string().nullable(),
    tertiaryColor: z.string().nullable(),
    numSlides: z.number(),
    minWords: z.number(),
    maxWords: z.number(),
  })
  .strict();

type SlideRequestDto = z.infer<typeof SlideRequestDtoSchema>;
export default SlideRequestDto;
