
-----------------------------------------------------------------
<h3>Kafka Integration Part II. Dispatcher Service</h3>
<h4>Dispatcher Service вычитывает сообщение <code>AvroOrderPlacedEvent</code> из топика <code>v1.public.orders_outbox</code>, обрабатывает заказ и отправляет <code>OrderDispatchedEvent</code> в топик <code>v1.orders_dispatch</code>.
</h4>