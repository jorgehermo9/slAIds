import { z } from "zod";

export const PresentationRequestDtoSchema = z
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
    bulletPoints: z.boolean(),
  })
  .strict();

type SlideRequestDto = z.infer<typeof PresentationRequestDtoSchema>;
export default SlideRequestDto;
