import asyncio
import logging
import os
from aiokafka import AIOKafkaConsumer
from aiokafka.structs import ConsumerRecord
from cloudevents.kafka import from_binary, KafkaMessage

consumer_group_id = os.environ["APPLICATION_CONSUMER_GROUP_ID"]
logger = logging.getLogger(consumer_group_id)

logging.basicConfig(level=logging.INFO, force=True)

def to_message(record: ConsumerRecord):
    return KafkaMessage(dict(record.headers), record.key, record.value)

async def consume():
    consumer = AIOKafkaConsumer(
        os.environ["APPLICATION_CONSUMER_KAFKA_TOPIC"],
        group_id=consumer_group_id,
        bootstrap_servers=["kafka:29092"],
    )

    await consumer.start()

    try:
        async for record in consumer:
            cloudevent = from_binary(to_message(record))
            logger.info(cloudevent)
    finally:
        await consumer.stop()


asyncio.run(consume())
