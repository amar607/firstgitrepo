import { Post } from "./post";

export interface PaginatedPostsResponse<T> {
  content: Post[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}