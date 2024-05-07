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
import { useSession } from "next-auth/react";

import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const formSchema = z.object({
  name: z.string().min(1, {
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
  deadline: z.date().refine((value) => {
    return value instanceof Date; 
  }, "Invalid date format. Expected a Date object."),
  totalCost: z.number().min(0, {
    message: "Total cost must be a non-negative number.",
  }),
  subscribers: z.array(z.string()),
  state: z.enum(["OPEN", "CLOSED"]),
});



export default function ProductForm() {
  const { data: session } = useSession();
  const sessionToken = session?.user.value.token;
  const form = useForm({ resolver: zodResolver(formSchema) });

  const onSubmit = async () => {
    try {
      const formData = form.getValues();
      const res = await fetch("http://localhost:8080/api/v1/products", {
        method: "POST",
        headers: {
          Accept: "application/json",
          Authorization: `Bearer ${sessionToken}`,
        },
        body: JSON.stringify(formData),
      });
  
    
    } catch (error) {
      console.error('Error submitting form:', error.message);
    } 
  };

  const handleFormSubmit = (e) => {
    e.preventDefault();
    console.log('Submitting form...');
    onSubmit(); // Llama a la función onSubmit al hacer clic en el botón "Submit"
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
                <Input
                  placeholder="Product's name"
                  value={field.value || ''} // Asegúrate de manejar el valor de forma controlada
                  onChange={(e) => field.onChange(e.target.value)} // Define un onChange incluso si no haces nada en él
                />

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
                <Input
                  placeholder="https://upload.wikimedia.org/..."
                  value={field.value || ''} // Asegúrate de manejar el valor de forma controlada
                  onChange={(e) => field.onChange(e.target.value)} // Define un onChange incluso si no haces nada en él
                />

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
                <Input
                  placeholder="https://www.my-product.com"
                  value={field.value || ''} // Asegúrate de manejar el valor de forma controlada
                  onChange={(e) => field.onChange(e.target.value)} // Define un onChange incluso si no haces nada en él
                />
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
                <Input
                  type="number"
                  inputMode="numeric"
                  placeholder="3"
                  onChange={(e) => {
                    const value = parseInt(e.target.value, 10);
                    field.onChange(value);
                  }}
                />
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
                <Input
                  type="number"
                  inputMode="numeric"
                  placeholder="1"
                  onChange={(e) => {
                    const value = parseInt(e.target.value, 10);
                    field.onChange(value);
                  }}
                />
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
                <DatePicker
                  selected={field.value}
                  onChange={(date) => field.onChange(date)}
                  showTimeInput
                  dateFormat="yyyy-MM-dd'T'HH:mm:ss'Z'"
                  placeholderText="Select deadline"
                />
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
                <Input
                  type="number"
                  step="0.01"
                  placeholder="30.6"
                  onChange={(e) => {
                    const value = parseFloat(e.target.value);
                    field.onChange(value);
                  }}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit" onClick={handleFormSubmit}>Submit</Button>
      </form>
    </Form>
  );
}
