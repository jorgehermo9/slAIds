import PresentationFile from "@/entities/PresentationFile";
import Slide from "@/entities/Slide";
import { SlideDtoSchema, toSlide } from "@/entities/SlideDto";
import SlideRequest, { toSlideRequestDto } from "@/entities/SlideRequest";
import User from "@/entities/User";

export default abstract class UserService {
  private static endpoint = "/api";


  static async signup(user: User): Promise<string> {

    return fetch(`${this.endpoint}/users/signup`, {
      keepalive: true,
      method: "POST",
      body: JSON.stringify(user),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error(res.statusText);
        return res.json();
      })
      .then((res) => res.serviceToken);
  }
}
