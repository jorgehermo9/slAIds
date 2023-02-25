import { z } from "zod";
import Presentation from "./Presentation";

export const PresentationDtoSchema = z
  .object({
    id: z.number(),
    title: z.string(),
    description: z.string(),
    image: z.string(),
    createdAt: z.string(),
    updatedAt: z.string(),
  })
  .strict();

type PresentationDto = z.infer<typeof PresentationDtoSchema>;
export default PresentationDto;

export const toPresentation = (
  presentationDto: PresentationDto
): Presentation => {
  return {
    id: presentationDto.id,
    title: presentationDto.title,
    description: presentationDto.description,
    image: presentationDto.image,
    createdAt: presentationDto.createdAt,
    updatedAt: presentationDto.updatedAt,
  };
};
