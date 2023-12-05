import asyncio
import logging
import os
from aiokafka import AIOKafkaConsumer
from cloudevents import from_json

consumer_group_id = os.environ["APPLICATION_CONSUMER_GROUP_ID"]
logger = logging.getLogger(consumer_group_id)

logging.basicConfig(level=logging.INFO, force=True)

async def consume():
    consumer = AIOKafkaConsumer(
        os.environ["APPLICATION_CONSUMER_KAFKA_TOPIC"],
        group_id=consumer_group_id,
        bootstrap_servers=["kafka:29092"],
        value_deserializer=lambda x: from_json(x) # TODO!
    )

    await consumer.start()

    try:
        async for message in consumer:
            logger.info(message.value.decode())
    finally:
        await consumer.stop()


asyncio.run(consume())
