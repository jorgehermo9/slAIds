import { z } from "zod";

export const SlideRequestSchema = z
  .object({
    title: z.string(),
    description: z.string(),
    fontFamily: z.string(),
    primaryColor: z.string(),
    secondaryColor: z.string().nullable(),
    tertiaryColor: z.string().nullable(),
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
  };
};
