import CustomDrawerContent from "@/components/home&others/CustomDrawer";
import { Drawer } from "expo-router/drawer";
import React from "react";

export default function RootLayout() {
  return (
    <Drawer
      drawerContent={(props) => <CustomDrawerContent {...props}/>}
      
      screenOptions={{
        headerShown: false, // O header customizado virá do layout das Tabs
        drawerStyle: { width: '70%' }
      }}
    >
      <Drawer.Screen
        name="(tabs)"
        options={{ drawerLabel: "Menu Principal" }}
      />
      {/* Adicione aqui outras rotas secundárias do Drawer, se quiser */}
    </Drawer>
  );
}