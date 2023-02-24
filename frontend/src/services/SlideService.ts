import PresentationFile from "@/entities/PresentationFile";
import Slide from "@/entities/Slide";
import { SlideDtoSchema, toSlide } from "@/entities/SlideDto";
import SlideRequest from "@/entities/SlideRequest";

export default abstract class SlideService {
  private static endpoint = "/api";

  static async createPresentation(
    slideRequest: SlideRequest
  ): Promise<PresentationFile> {
    return fetch(`${this.endpoint}/slides`, {
      method: "POST",
      body: JSON.stringify(slideRequest),
      headers: {
        "Content-Type": "application/json",
      },
    }).then((res) => res.blob());
  }

  static async getPresentation(id: Slide["id"]): Promise<Slide> {
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
