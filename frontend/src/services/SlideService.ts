import PresentationFile from "@/entities/PresentationFile";
import Slide from "@/entities/Slide";
import { SlideDtoSchema, toSlide } from "@/entities/SlideDto";
import SlideRequest, { toSlideRequestDto } from "@/entities/SlideRequest";
import SlideRequestDto from "@/entities/SlideRequestDto";

export default abstract class SlideService {
  private static endpoint = "/api";

  static async generatePresentation(
    slideRequest: SlideRequest
  ): Promise<number> {
    const slideRequestDto: SlideRequestDto = toSlideRequestDto(slideRequest);
    return fetch(`${this.endpoint}/presentations/generate`, {
      method: "POST",
      body: JSON.stringify(slideRequestDto),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error(res.statusText);
        return res.text();
      })
      .then((res) => parseInt(res));
  }

  static async isAvailable(id: Slide["id"]): Promise<boolean> {
    return fetch(`${this.endpoint}/presentations/${id}/is-available`, {
      method: "GET",
    })
      .then((res) => {
        if (!res.ok) throw new Error(res.statusText);
        return res.text();
      })
      .then((res) => res === "true");
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
