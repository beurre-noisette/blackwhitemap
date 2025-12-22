import { ImgHTMLAttributes } from "react";
import { cn } from "../utils/cn";

import catchtable from "../assets/icons/catchtable.svg";
import naver from "../assets/icons/naver.svg";
import chefBlack from "../assets/icons/chef-black.svg";
import chefWhite from "../assets/icons/chef-white.svg";
import watch from "../assets/icons/watch.svg";
import price from "../assets/icons/price.svg";
import share from "../assets/icons/share.svg";
import category from "../assets/icons/category.svg";
import location from "../assets/icons/location.svg";
import usdCircle from "../assets/icons/usdCircle.svg";

export type IconName =
  | "catchtable"
  | "naver"
  | "chef-black"
  | "chef-white"
  | "watch"
  | "price"
  | "share"
  | "category"
  | "location"
  | "usdCircle";

export type IconSize = "extraSmall" | "small" | "medium" | "large";

export interface IconProps extends Omit<
  ImgHTMLAttributes<HTMLImageElement>,
  "src" | "alt"
> {
  /**
   * 아이콘 이름
   *
   * @example
   * <Icon name="catchtable" />
   */
  name: IconName;

  /**
   * 아이콘 크기
   * - extraSmall: 12 x 12 (휴무일, 가격 아이콘)
   * - small: 16 x 16
   * - medium: 20 x 20 (default)
   * - large: 24 x 24
   */
  size?: IconSize;

  /**
   * 접근성을 위한 대체 텍스트
   *
   * 지정하지 않을 경우 아이콘 이름을 사용합니다.
   */
  alt?: string;
}

/**
 * 아이콘 이름과 실제 파일 매핑
 *
 * 새로운 아이콘을 추가하려면 여기에 추가하면 됩니다.
 */
export const ICON_MAP: Record<IconName, string> = {
  catchtable: catchtable,
  naver: naver,
  "chef-black": chefBlack,
  "chef-white": chefWhite,
  watch: watch,
  price: price,
  share: share,
  category: category,
  location: location,
  usdCircle: usdCircle,
};

/**
 * 아이콘 크기 매핑 (Tailwind 클래스 사용)
 */
const SIZE_MAP: Record<IconSize, string> = {
  extraSmall: "w-3 h-3", // 12 x 12
  small: "w-4 h-4", // 16 x 16
  medium: "w-5 h-5", // 20 x 20 (default)
  large: "w-6 h-6", // 24 x 24
};

export const Icon = ({
  name,
  size = "medium",
  alt,
  className,
  ...props
}: IconProps) => {
  const iconSrc = ICON_MAP[name];
  const sizeClass = SIZE_MAP[size];

  return (
    <img
      src={iconSrc}
      alt={alt || name}
      className={cn(
        "flex-shrink-0", // 크기 고정
        sizeClass,
        className,
      )}
      {...props}
    />
  );
};
