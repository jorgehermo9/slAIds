import { z } from "zod";
import PresentationRequestDto from "./PresentationRequestDto";

export const PresentationRequestSchema = z
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

type PresentationRequest = z.infer<typeof PresentationRequestSchema>;
export default PresentationRequest;

export const getDefaultPresentationRequest = (): PresentationRequest => {
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

export const toPresentationRequestDto = (
  presentationRequest: PresentationRequest
): PresentationRequestDto => {
  return {
    title: presentationRequest.title,
    prompt: presentationRequest.description,
    fontFamily: presentationRequest.fontFamily,
    primaryColor: presentationRequest.primaryColor,
    secondaryColor: presentationRequest.secondaryColor,
    tertiaryColor: presentationRequest.tertiaryColor,
    numSlides: presentationRequest.numSlides,
    minWords: presentationRequest.minWords,
    maxWords: presentationRequest.maxWords,
    bulletPoints: presentationRequest.bulletPoints,
  };
};
