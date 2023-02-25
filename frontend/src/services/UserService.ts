import PresentationFile from "@/entities/PresentationFile";
import Slide from "@/entities/Slide";
import { SlideDtoSchema, toSlide } from "@/entities/SlideDto";
import SlideRequest, { toSlideRequestDto } from "@/entities/SlideRequest";
import User from "@/entities/User";

import SlideRequestDto from "@/entities/SlideRequestDto";

export default abstract class SlideService {
  private static endpoint = "/api";

  static async generate(user: SlideRequest): Promise<void> {
    return fetch(`${this.endpoint}/generate`, {
      keepalive: true,
      method: "POST",
      body: JSON.stringify(user),
      headers: {
        "Keep-Alive": "timeout=1000, max=0",
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error(res.statusText);
        res.json();
      })
      .then((res) => console.log(res));
  }
}
