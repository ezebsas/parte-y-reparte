"use client"
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";

const formSchema = z.object({
  name: z.string().nonempty({
    message: "Name is required.",
  }),
  image: z.string().url({
    message: "Image must be a valid URL.",
  }),
  link: z.string().url({
    message: "Link must be a valid URL.",
  }),
  maxPeople: z.number().int().min(1, {
    message: "Maximum people must be at least 1.",
  }),
  minPeople: z.number().int().min(1, {
    message: "Minimum people must be at least 1.",
  }),
  deadline: z.string().regex(
    /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}Z$/,
    "Invalid date format. Expected: YYYY-MM-DDTHH:MM:SSZ"
  ),
  totalCost: z.number().min(0, {
    message: "Total cost must be a non-negative number.",
  }),
  subscribers: z.array(z.string()),
  state: z.enum(["OPEN", "CLOSED"]),
});

export default function ProductForm() {
  const form = useForm({ resolver: zodResolver(formSchema) });
//mandar al back
  const onSubmit = (data) => {
    console.log(data); 
  };

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
        <FormField
          control={form.control}
          name="name"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Name</FormLabel>
              <FormControl>
                <Input placeholder="Product's name" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="image"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Image URL</FormLabel>
              <FormControl>
                <Input placeholder="https://upload.wikimedia.org/..." {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="link"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Link URL</FormLabel>
              <FormControl>
                <Input placeholder="www.my-product.com" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="maxPeople"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Maximum People</FormLabel>
              <FormControl>
                <Input type="number" placeholder="3" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="minPeople"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Minimum People</FormLabel>
              <FormControl>
                <Input type="number" placeholder="2" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="deadline"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Deadline</FormLabel>
              <FormControl>
                <Input type="datetime-local" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="totalCost"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Total Cost</FormLabel>
              <FormControl>
                <Input type="number" step="0.01" placeholder="33.3" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit">Submit</Button>
      </form>
    </Form>
  );
}
