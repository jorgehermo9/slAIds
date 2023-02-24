import { z } from "zod";
import Slide from "./Slide";

export const SlideDtoSchema = z
  .object({
    id: z.number(),
    title: z.string(),
    description: z.string(),
    image: z.string(),
    createdAt: z.string(),
    updatedAt: z.string(),
  })
  .strict();

type SlideDto = z.infer<typeof SlideDtoSchema>;
export default SlideDto;

export const toSlide = (slideDto: SlideDto): Slide => {
  return {
    id: slideDto.id,
    title: slideDto.title,
    description: slideDto.description,
    image: slideDto.image,
    createdAt: slideDto.createdAt,
    updatedAt: slideDto.updatedAt,
  };
};
