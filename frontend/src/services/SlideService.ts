import Slide from "@/entities/Slide";
import { SlideDtoSchema, toSlide } from "@/entities/SlideDto";

export default abstract class SlideService {
  private static endpoint = "/api";

  static async getSlides(id: Slide["id"]): Promise<Slide[]> {
    return fetch(`${this.endpoint}/slides/${id}`, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((res) => {
        const slideDto = SlideDtoSchema.parse(res);
        return toSlide(res);
      });
  }
}
