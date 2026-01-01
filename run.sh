#!/bin/bash

echo "🐾 Starting Digital Pet..."
echo "=========================="

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven first."
    echo "   Run: brew install maven"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java first."
    exit 1
fi

echo "✅ Dependencies found!"
echo "🚀 Launching Digital Pet Console App..."
echo ""

# Run the console application
mvn clean compile exec:java -Dexec.mainClass="digitalpet.ConsoleApp" -q

echo ""
echo "👋 Thanks for playing with your Digital Pet!"