import { z } from "zod";
import SlideRequestDto from "./SlideRequestDto";

export const SlideRequestSchema = z
  .object({
    title: z.string(),
    description: z.string(),
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

type SlideRequest = z.infer<typeof SlideRequestSchema>;
export default SlideRequest;

export const getDefaultSlideRequest = (): SlideRequest => {
  return {
    title: "",
    description: "",
    fontFamily: "",
    primaryColor: "",
    secondaryColor: null,
    tertiaryColor: null,
    numSlides: 1,
    minWords: 50,
    maxWords: 60,
    bulletPoints: false,
  };
};

export const toSlideRequestDto = (
  slideRequest: SlideRequest
): SlideRequestDto => {
  return {
    title: slideRequest.title,
    prompt: slideRequest.description,
    fontFamily: slideRequest.fontFamily,
    primaryColor: slideRequest.primaryColor,
    secondaryColor: slideRequest.secondaryColor,
    tertiaryColor: slideRequest.tertiaryColor,
    numSlides: slideRequest.numSlides,
    minWords: slideRequest.minWords,
    maxWords: slideRequest.maxWords,
  };
};
