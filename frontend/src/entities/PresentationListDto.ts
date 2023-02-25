import { z } from "zod";

export const PresentationListDtoSchema = z
  .object({
    id: z.number(),
    title: z.string(),
  })
  .strict();

type PresentationListDto = z.infer<typeof PresentationListDtoSchema>;
export default PresentationListDto;
